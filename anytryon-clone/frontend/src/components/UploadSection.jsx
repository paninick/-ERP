
import { useState } from 'react'

function UploadSection({ modelImage, setModelImage, topImage, setTopImage, bottomImage, setBottomImage }) {
  const handleFileChange = (setter) =&gt; (e) =&gt; {
    const file = e.target.files[0]
    if (file) {
      const url = URL.createObjectURL(file)
      setter(file)
    }
  }

  const UploadArea = ({ label, image, setImage, sublabel, required = false }) =&gt; {
    return (
      &lt;div className="space-y-2"&gt;
        &lt;label className="block text-sm font-medium text-gray-700"&gt;
          {label} {required &amp;&amp; &lt;span className="text-red-500"&gt;*&lt;/span&gt;}
        &lt;/label&gt;
        {sublabel &amp;&amp; (
          &lt;p className="text-xs text-gray-500"&gt;{sublabel}&lt;/p&gt;
        )}
        &lt;label className="upload-area block"&gt;
          &lt;input
            type="file"
            accept="image/*"
            onChange={handleFileChange(setImage)}
            className="hidden"
          /&gt;
          {image ? (
            &lt;div className="relative"&gt;
              &lt;img
                src={URL.createObjectURL(image)}
                alt={label}
                className="w-full h-48 object-cover rounded-lg"
              /&gt;
              &lt;button
                onClick={(e) =&gt; {
                  e.preventDefault()
                  setImage(null)
                }}
                className="absolute top-2 right-2 bg-red-500 text-white rounded-full p-1 hover:bg-red-600 transition-colors"
              &gt;
                &lt;svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"&gt;
                  &lt;path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" /&gt;
                &lt;/svg&gt;
              &lt;/button&gt;
            &lt;/div&gt;
          ) : (
            &lt;div className="py-8"&gt;
              &lt;svg className="w-12 h-12 mx-auto text-gray-400 mb-3" fill="none" stroke="currentColor" viewBox="0 0 24 24"&gt;
                &lt;path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" /&gt;
              &lt;/svg&gt;
              &lt;p className="text-gray-600"&gt;点击或拖拽上传&lt;/p&gt;
              &lt;p className="text-xs text-gray-400 mt-1"&gt;PNG, JPG 或 WebP&lt;/p&gt;
            &lt;/div&gt;
          )}
        &lt;/label&gt;
      &lt;/div&gt;
    )
  }

  return (
    &lt;div className="card"&gt;
      &lt;h2 className="text-xl font-bold text-gray-900 mb-6"&gt;📸 上传素材&lt;/h2&gt;
      
      &lt;div className="space-y-6"&gt;
        &lt;UploadArea
          label="人物照"
          sublabel="清晰、光线好的照片效果更稳"
          image={modelImage}
          setImage={setModelImage}
          required={true}
        /&gt;
        
        &lt;div className="grid md:grid-cols-2 gap-4"&gt;
          &lt;UploadArea
            label="上衣图"
            image={topImage}
            setImage={setTopImage}
            required={true}
          /&gt;
          
          &lt;UploadArea
            label="下装图 (可选)"
            sublabel="加上下装会更完整"
            image={bottomImage}
            setImage={setBottomImage}
          /&gt;
        &lt;/div&gt;
      &lt;/div&gt;
    &lt;/div&gt;
  )
}

export default UploadSection

