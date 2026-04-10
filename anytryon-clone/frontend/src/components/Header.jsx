
function Header() {
  return (
    &lt;header className="bg-white shadow-sm border-b border-gray-200"&gt;
      &lt;div className="container mx-auto px-4 py-4"&gt;
        &lt;div className="flex items-center justify-between"&gt;
          &lt;div className="flex items-center gap-3"&gt;
            &lt;div className="w-10 h-10 bg-gradient-to-br from-primary-500 to-primary-700 rounded-xl flex items-center justify-center"&gt;
              &lt;span className="text-white text-xl font-bold"&gt;👗&lt;/span&gt;
            &lt;/div&gt;
            &lt;h1 className="text-2xl font-bold text-gray-900"&gt;AnyTryOn&lt;/h1&gt;
          &lt;/div&gt;
          
          &lt;nav className="flex items-center gap-6"&gt;
            &lt;a href="#" className="text-gray-600 hover:text-primary-600 font-medium transition-colors"&gt;
              首页
            &lt;/a&gt;
            &lt;a href="#" className="text-gray-600 hover:text-primary-600 font-medium transition-colors"&gt;
              常见问题
            &lt;/a&gt;
            &lt;button className="btn-primary"&gt;
              登录
            &lt;/button&gt;
          &lt;/nav&gt;
        &lt;/div&gt;
      &lt;/div&gt;
    &lt;/header&gt;
  )
}

export default Header

