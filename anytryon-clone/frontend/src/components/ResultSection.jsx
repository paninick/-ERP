
function ResultSection({ resultImage }) {
  return (
    &lt;div className="card"&gt;
      &lt;h2 className="text-xl font-bold text-gray-900 mb-6"&gt;✨ 结果示例&lt;/h2&gt;
      
      {resultImage ? (
        &lt;div className="text-center"&gt;
          &lt;img
            src={resultImage}
            alt="试穿结果"
            className="w-full max-w-2xl mx-auto rounded-xl shadow-lg mb-6"
          /&gt;
          &lt;div className="flex gap-4 justify-center"&gt;
            &lt;button
              onClick={() =&gt; {
                const a = document.createElement('a')
                a.href = resultImage
                a.download = 'tryon-result.png'
                a.click()
              }}
              className="btn-primary"
            &gt;
              💾 下载图片
            &lt;/button&gt;
            &lt;button className="btn-secondary"&gt;
              🔗 分享
            &lt;/button&gt;
          &lt;/div&gt;
        &lt;/div&gt;
      ) : (
        &lt;div className="text-center py-12"&gt;
          &lt;div className="w-24 h-24 mx-auto bg-gray-100 rounded-full flex items-center justify-center mb-4"&gt;
            &lt;svg className="w-12 h-12 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"&gt;
              &lt;path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" /&gt;
            &lt;/svg&gt;
          &lt;/div&gt;
          &lt;p className="text-gray-500 text-lg"&gt;
            上传素材并点击生成，试穿效果将在这里显示
          &lt;/p&gt;
          &lt;p className="text-gray-400 text-sm mt-2"&gt;
            通常 1 分钟左右出图 ⏱️
          &lt;/p&gt;
        &lt;/div&gt;
      )}
    &lt;/div&gt;
  )
}

export default ResultSection

