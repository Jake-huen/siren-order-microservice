import React from 'react'
import styled from 'styled-components'

// 스타일드 컴포넌트 정의
const ProductCard = styled.div`
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
`

const ProductDetail = styled.div`
  margin-bottom: 10px;
`

const Label = styled.span`
  font-weight: bold;
`

const Value = styled.span`
  margin-left: 8px;
`

const UserOrder = ({ product }) => {
  return (
    <ProductCard>
      <ProductDetail>
        <Label>Product ID:</Label>
        <Value>{product.productId}</Value>
      </ProductDetail>
      <ProductDetail>
        <Label>Quantity:</Label>
        <Value>{product.qty}</Value>
      </ProductDetail>
      <ProductDetail>
        <Label>Unit Price:</Label>
        <Value>{product.unitPrice}</Value>
      </ProductDetail>
      <ProductDetail>
        <Label>Total Price:</Label>
        <Value>{product.totalPrice}</Value>
      </ProductDetail>
      <ProductDetail>
        <Label>Order Status:</Label>
        <Value>{product.orderStatus}</Value>
      </ProductDetail>
      <ProductDetail>
        <Label>Order ID:</Label>
        <Value>{product.orderId}</Value>
      </ProductDetail>
    </ProductCard>
  )
}

export default UserOrder
