
import { useState } from 'react'
import Header from './components/Header'
import UploadSection from './components/UploadSection'
import StyleSelector from './components/StyleSelector'
import ResultSection from './components/ResultSection'
import Footer from './components/Footer'
import { useTryOn } from './hooks/useTryOn'
import { useImageUpload } from './hooks/useImageUpload'

function App() {
  const [selectedStyle, setSelectedStyle] = useState('original')
  
  const { images, setImage, removeImage } = useImageUpload()
  const { isGenerating, resultImage, error, generateTryOn, reset } = useTryOn()

  const handleGenerate = async () =&gt; {
    if (!images.model || !images.top) {
      alert('请上传人物照和上衣图片！')
      return
    }

    const formData = new FormData()
    formData.append('model_image', images.model)
    formData.append('top_image', images.top)
    if (images.bottom) {
      formData.append('bottom_image', images.bottom)
    }
    formData.append('style', selectedStyle)

    try {
      await generateTryOn(formData)
    } catch (err) {
      console.error('生成失败:', err)
      alert(error || '生成失败，请重试！')
    }
  }

  return (
    &lt;div className="min-h-screen flex flex-col"&gt;
      &lt;Header /&gt;
      
      &lt;main className="flex-1 container mx-auto px-4 py-8"&gt;
        &lt;div className="text-center mb-12"&gt;
          &lt;h1 className="text-4xl font-bold text-gray-900 mb-4"&gt;
            🎨 AI 虚拟试穿
          &lt;/h1&gt;
          &lt;p className="text-xl text-gray-600"&gt;
            上传人物照和衣服图，一键生成真实试穿效果！
          &lt;/p&gt;
        &lt;/div&gt;

        &lt;div className="grid lg:grid-cols-2 gap-8 mb-8"&gt;
          &lt;UploadSection
            modelImage={images.model}
            setModelImage={(file) =&gt; setImage('model', file)}
            topImage={images.top}
            setTopImage={(file) =&gt; setImage('top', file)}
            bottomImage={images.bottom}
            setBottomImage={(file) =&gt; setImage('bottom', file)}
          /&gt;
          
          &lt;StyleSelector
            selectedStyle={selectedStyle}
            setSelectedStyle={setSelectedStyle}
          /&gt;
        &lt;/div&gt;

        &lt;div className="text-center mb-8"&gt;
          &lt;button
            onClick={handleGenerate}
            disabled={isGenerating}
            className="btn-primary text-lg py-3 px-8 disabled:opacity-50 disabled:cursor-not-allowed"
          &gt;
            {isGenerating ? (
              &lt;span className="flex items-center gap-2"&gt;
                &lt;svg className="animate-spin h-5 w-5" viewBox="0 0 24 24"&gt;
                  &lt;circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" fill="none" /&gt;
                  &lt;path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" /&gt;
                &lt;/svg&gt;
                正在生成...
              &lt;/span&gt;
            ) : (
              '✨ 一键生成试穿效果'
            )}
          &lt;/button&gt;
        &lt;/div&gt;

        &lt;ResultSection resultImage={resultImage} /&gt;
      &lt;/main&gt;

      &lt;Footer /&gt;
    &lt;/div&gt;
  )
}

export default App

