
import { useState, useCallback } from 'react'
import { tryOnAPI } from '../services/api'

export const useTryOn = () =&gt; {
  const [isGenerating, setIsGenerating] = useState(false)
  const [resultImage, setResultImage] = useState(null)
  const [error, setError] = useState(null)

  const generateTryOn = useCallback(async (formData) =&gt; {
    setIsGenerating(true)
    setError(null)
    setResultImage(null)

    try {
      const response = await tryOnAPI.generate(formData)
      const url = URL.createObjectURL(response.data)
      setResultImage(url)
      return url
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.message || '生成失败，请重试'
      setError(errorMessage)
      throw err
    } finally {
      setIsGenerating(false)
    }
  }, [])

  const reset = useCallback(() =&gt; {
    setResultImage(null)
    setError(null)
    setIsGenerating(false)
  }, [])

  return {
    isGenerating,
    resultImage,
    error,
    generateTryOn,
    reset,
  }
}

