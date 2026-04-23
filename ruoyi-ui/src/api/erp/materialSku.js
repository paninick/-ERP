import request from "@/utils/request"

export function listMaterialSkuOptions(query) {
  return request({
    url: "/erp/material/sku/options",
    method: "get",
    params: query
  })
}
