import os
from dotenv import load_dotenv

load_dotenv()

def get_db_config():
    """Get database configuration from environment variables
    
    Reads from .env file or system environment variables.
    Falls back to default values if not set.
    """
    return {
        'host': os.environ.get('DB_HOST', 'localhost'),
        'port': int(os.environ.get('DB_PORT', '3306')),
        'user': os.environ.get('DB_USER', 'root'),
        'password': os.environ.get('DB_PASSWORD', ''),
        'database': os.environ.get('DB_NAME', 'ry_vue'),
        'charset': os.environ.get('DB_CHARSET', 'utf8mb4')
    }

def get_connection():
    """Get a pymysql connection using the configured database"""
    import pymysql
    config = get_db_config()
    return pymysql.connect(**config)
