import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { useQuery } from 'react-query'
import { Link } from 'react-router-dom'
import { getUserOrders, getSuccessOrders, getFailedOrders } from '../api'
import { useRecoilValue } from 'recoil'
import { UserIdAtom } from '../recoil/TokenAtom'
import UserOrder from './UserOrder'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 300px;
`

const OrderList = styled.ul`
  margin-top: 30px;
  margin-bottom: 100px;
  list-style: none;
  padding: 0;
`

const OrderItem = styled.li`
  border: 1px solid #ddd;
  color: white;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
  width: 1000px;
`

const Loading = styled.div`
  text-align: center;
  color: white;
`

const Error = styled.div`
  color: red;
  text-align: center;
`

function Admin() {
  const userId = useRecoilValue(UserIdAtom)
  const {
    data: successOrders,
    isLoading: isSuccessLoading,
    isError: isSuccessError,
  } = useQuery(['successOrders'], () => getSuccessOrders())

  const {
    data: failedOrders,
    isLoading: isFailedLoading,
    isError: isFailedError,
  } = useQuery(['failedOrders'], () => getFailedOrders())

  const {
    data: userOrders,
    isLoading: isUserOrdersLoading,
    isError: isUserOrdersError,
  } = useQuery(['userOrders', userId], () => getUserOrders(userId))

  if (isSuccessLoading || isFailedLoading || isUserOrdersLoading) {
    return <Loading>Loading orders...</Loading>
  }

  if (isSuccessError || isFailedError || isUserOrdersError) {
    return <Error>Error loading orders.</Error>
  }

  return (
    <Container>
      <h2>Success Orders</h2>
      <OrderList>
        {successOrders?.map(order => (
          <OrderItem key={order.orderId}>
            Order ID: {order.orderId} - Status: {order.status}
          </OrderItem>
        ))}
      </OrderList>

      <h2>Failed Orders</h2>
      <OrderList>
        {failedOrders?.map(order => (
          <OrderItem key={order.orderId}>
            Order ID: {order.orderId} - Status: {order.status}
          </OrderItem>
        ))}
      </OrderList>

      <h2>로그인 사용자 주문 상태</h2>
      <OrderList>
        {userOrders?.map(order => (
          <UserOrder key={order.orderId} product={order} />
        ))}
      </OrderList>
    </Container>
  )
}
export default Admin
