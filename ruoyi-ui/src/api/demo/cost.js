import request from '@/utils/request'

// 计算FOB价格
export function calculateFobPrice(styleNo, quantity, profitRate) {
  return request({
    url: '/demo/cost/calculateFobPrice',
    method: 'get',
    params: { styleNo, quantity, profitRate }
  })
}

// 计算CIF价格
export function calculateCifPrice(fobPrice, freight, insurance) {
  return request({
    url: '/demo/cost/calculateCifPrice',
    method: 'get',
    params: { fobPrice, freight, insurance }
  })
}

// 计算CNF价格
export function calculateCnfPrice(fobPrice, freight) {
  return request({
    url: '/demo/cost/calculateCnfPrice',
    method: 'get',
    params: { fobPrice, freight }
  })
}

// 计算EXW价格
export function calculateExwPrice(costPrice, profitRate) {
  return request({
    url: '/demo/cost/calculateExwPrice',
    method: 'get',
    params: { costPrice, profitRate }
  })
}

// 计算订单成本
export function calculateOrderCost(orderId) {
  return request({
    url: `/demo/cost/calculateOrderCost/${orderId}`,
    method: 'get'
  })
}

// 计算订单利润
export function calculateOrderProfit(orderId) {
  return request({
    url: `/demo/cost/calculateOrderProfit/${orderId}`,
    method: 'get'
  })
}

// 计算订单利润率
export function calculateOrderProfitRate(orderId) {
  return request({
    url: `/demo/cost/calculateOrderProfitRate/${orderId}`,
    method: 'get'
  })
}

// 计算款式成本
export function calculateStyleCost(style) {
  return request({
    url: '/demo/cost/calculateStyleCost',
    method: 'post',
    data: style
  })
}

// 计算订单价格明细
export function calculateOrderPriceDetails(order) {
  return request({
    url: '/demo/cost/calculateOrderPriceDetails',
    method: 'post',
    data: order
  })
}
