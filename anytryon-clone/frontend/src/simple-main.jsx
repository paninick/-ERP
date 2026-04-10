
import React from 'react'
import ReactDOM from 'react-dom/client'

function SimpleApp() {
  return (
    &lt;div style={{ 
      padding: '50px', 
      textAlign: 'center',
      fontFamily: 'Arial, sans-serif'
    }}&gt;
      &lt;h1 style={{ color: '#3b82f6' }}&gt;🎉 AnyTryOn - AI 虚拟试穿&lt;/h1&gt;
      &lt;p style={{ fontSize: '20px', color: '#666' }}&gt;
        如果你能看到这个页面，说明 React 正常工作！
      &lt;/p&gt;
      &lt;div style={{ 
        marginTop: '30px',
        padding: '20px',
        background: '#f0f9ff',
        borderRadius: '10px',
        border: '1px solid #bae6fd'
      }}&gt;
        &lt;h3&gt;下一步：&lt;/h3&gt;
        &lt;p&gt;请访问 &lt;a href="http://localhost:3000" style={{ color: '#3b82f6' }}&gt;http://localhost:3000&lt;/a&gt;&lt;/p&gt;
      &lt;/div&gt;
    &lt;/div&gt;
  )
}

ReactDOM.createRoot(document.getElementById('root')).render(
  &lt;React.StrictMode&gt;
    &lt;SimpleApp /&gt;
  &lt;/React.StrictMode&gt;,
)

