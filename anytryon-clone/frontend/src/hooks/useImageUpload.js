
import { useState, useCallback } from 'react'

export const useImageUpload = () =&gt; {
  const [images, setImages] = useState({
    model: null,
    top: null,
    bottom: null,
  })

  const setImage = useCallback((type, file) =&gt; {
    setImages(prev =&gt; ({
      ...prev,
      [type]: file,
    }))
  }, [])

  const removeImage = useCallback((type) =&gt; {
    setImages(prev =&gt; ({
      ...prev,
      [type]: null,
    }))
  }, [])

  const reset = useCallback(() =&gt; {
    setImages({
      model: null,
      top: null,
      bottom: null,
    })
  }, [])

  return {
    images,
    setImage,
    removeImage,
    reset,
  }
}

