
export const formatFileSize = (bytes) =&gt; {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const sizes = ['Bytes', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

export const validateImageFile = (file) =&gt; {
  const errors = []
  
  if (!file) {
    errors.push('请选择文件')
    return errors
  }
  
  if (file.size &gt; MAX_FILE_SIZE) {
    errors.push(`文件大小不能超过 ${formatFileSize(MAX_FILE_SIZE)}`)
  }
  
  if (!ACCEPTED_IMAGE_TYPES.includes(file.type)) {
    errors.push('只支持 PNG, JPG, WebP 格式')
  }
  
  return errors
}

export const downloadImage = (url, filename = 'tryon-result.png') =&gt; {
  const a = document.createElement('a')
  a.href = url
  a.download = filename
  document.body.appendChild(a)
  a.click()
  document.body.removeChild(a)
}

