import time
import traceback
from typing import Callable, Any, Optional, Tuple
from .types import ErrorCode
from .exceptions import MCPException, TimeoutException


class RetryStrategy:
    def __init__(
        self,
        max_retries: int = 3,
        initial_delay_ms: int = 1000,
        backoff_factor: float = 2.0
    ):
        self.max_retries = max_retries
        self.initial_delay_ms = initial_delay_ms
        self.backoff_factor = backoff_factor

    def get_delay(self, retry_count: int) -> int:
        return int(self.initial_delay_ms * (self.backoff_factor ** retry_count))

    def should_retry(self, retry_count: int, exception: Exception) -> bool:
        if retry_count >= self.max_retries:
            return False

        if isinstance(exception, MCPException):
            return exception.retryable

        return False

    def execute_with_retry(
        self,
        func: Callable,
        *args,
        **kwargs
    ) -> Tuple[Any, bool]:
        retry_count = 0
        last_exception = None

        while retry_count <= self.max_retries:
            try:
                result = func(*args, **kwargs)
                return result, True
            except Exception as e:
                last_exception = e
                if not self.should_retry(retry_count, e):
                    break

                delay = self.get_delay(retry_count)
                time.sleep(delay / 1000.0)
                retry_count += 1

        return None, False


default_retry_strategies = {
    ErrorCode.TIMEOUT: RetryStrategy(max_retries=3, initial_delay_ms=1000, backoff_factor=2.0),
    ErrorCode.TOO_MANY_REQUESTS: RetryStrategy(max_retries=3, initial_delay_ms=2000, backoff_factor=2.0),
    ErrorCode.INTERNAL_ERROR: RetryStrategy(max_retries=2, initial_delay_ms=1000, backoff_factor=2.0),
}


def get_default_retry_strategy(error_code: ErrorCode) -> RetryStrategy:
    return default_retry_strategies.get(error_code, RetryStrategy(max_retries=0))


def capture_stack_trace() -> str:
    return traceback.format_exc()
