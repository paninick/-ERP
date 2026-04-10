
function StyleSelector({ selectedStyle, setSelectedStyle }) {
  const styles = [
    {
      id: 'original',
      name: '原生风格',
      description: '最接近真实的效果',
      color: 'from-blue-500 to-blue-700',
    },
    {
      id: 'daily',
      name: '日常风格',
      description: '自然随意的日常感',
      color: 'from-green-500 to-green-700',
    },
    {
      id: 'ecommerce',
      name: '电商风格',
      description: '专业的商品展示效果',
      color: 'from-purple-500 to-purple-700',
    },
    {
      id: 'fashion',
      name: '大片风格',
      description: '时尚大片质感',
      color: 'from-pink-500 to-pink-700',
    },
  ]

  return (
    &lt;div className="card"&gt;
      &lt;h2 className="text-xl font-bold text-gray-900 mb-6"&gt;🎨 外观设置&lt;/h2&gt;
      
      &lt;div className="space-y-4"&gt;
        &lt;div&gt;
          &lt;label className="block text-sm font-medium text-gray-700 mb-3"&gt;
            风格
          &lt;/label&gt;
          &lt;div className="grid grid-cols-2 gap-3"&gt;
            {styles.map((style) =&gt; (
              &lt;button
                key={style.id}
                onClick={() =&gt; setSelectedStyle(style.id)}
                className={`p-4 rounded-xl border-2 text-left transition-all ${
                  selectedStyle === style.id
                    ? 'border-primary-500 bg-primary-50'
                    : 'border-gray-200 hover:border-gray-300 hover:bg-gray-50'
                }`}
              &gt;
                &lt;div className={`w-8 h-8 rounded-lg bg-gradient-to-br ${style.color} mb-2`} /&gt;
                &lt;div className="font-semibold text-gray-900"&gt;{style.name}&lt;/div&gt;
                &lt;div className="text-xs text-gray-500"&gt;{style.description}&lt;/div&gt;
              &lt;/button&gt;
            ))}
          &lt;/div&gt;
        &lt;/div&gt;

        &lt;div className="pt-4 border-t border-gray-200"&gt;
          &lt;label className="block text-sm font-medium text-gray-700 mb-3"&gt;
            关注点
          &lt;/label&gt;
          &lt;div className="grid grid-cols-2 gap-3"&gt;
            &lt;button className="p-3 rounded-lg border-2 border-primary-500 bg-primary-50 text-center"&gt;
              &lt;div className="font-medium text-gray-900"&gt;服装更清晰&lt;/div&gt;
            &lt;/button&gt;
            &lt;button className="p-3 rounded-lg border-2 border-gray-200 hover:border-gray-300 text-center"&gt;
              &lt;div className="font-medium text-gray-900"&gt;更像本人&lt;/div&gt;
            &lt;/button&gt;
          &lt;/div&gt;
        &lt;/div&gt;
      &lt;/div&gt;
    &lt;/div&gt;
  )
}

export default StyleSelector

