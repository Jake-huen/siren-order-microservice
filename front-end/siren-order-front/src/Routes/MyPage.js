import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { Link } from 'react-router-dom'

import {
  userServiceHealthCheck,
  counterServiceHealthCheck,
  storeServiceHealthCheck,
  getAllUsers,
} from '../api'
import { useQuery } from 'react-query'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 100px;
`

const ServiceStatus = styled.div`
  margin-bottom: 20px;
`

const Heading = styled.h2`
  margin-top: 20px;
  font-size: 24px;
  margin-bottom: 20px;
`

const HeadingLink = styled(Link)`
  text-decoration: none;
  transition: color 0.3s ease;
  &:hover {
    color: blue;
  }
`

const Table = styled.table`
  width: 50%;
  border-collapse: collapse;
`

const TableHead = styled.thead`
  background-color: gray;
`

const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: green;
  }
  &:hover {
    background-color: green;
  }
`

const TableCell = styled.td`
  padding: 8px;
  border: 1px solid #ddd;
`

const TableHeaderCell = styled.th`
  padding: 8px;
  border: 2px solid #ddd;
  font-weight: bold;
`

const HeaderWithLink = ({ to, children }) => (
  <HeadingLink to={to}>
    <Heading>{children}</Heading>
  </HeadingLink>
)

const StyledLink = styled.a`
  text-decoration: none;
  color: white;
  font-weight: bold;
  transition: color 0.3s;

  &:hover {
    color: #007bff;
  }
`

function MyPage() {
  const [userServiceStatus, setUserServiceStatus] = useState({})
  const [counterServiceStatus, setCounterServiceStatus] = useState({})
  const [storeServiceStatus, setStoreServiceStatus] = useState({})
  const { data: users, isLoading } = useQuery('users', getAllUsers)

  useEffect(() => {
    // userService의 상태 가져오기
    userServiceHealthCheck()
      .then(data => {
        setUserServiceStatus(data)
      })
      .catch(error => {
        console.error('Failed to fetch user service health:', error)
        setUserServiceStatus({ status: 'Error' })
      })

    // counterService의 상태 가져오기
    counterServiceHealthCheck()
      .then(data => {
        setCounterServiceStatus(data)
      })
      .catch(error => {
        console.error('Failed to fetch counter service health:', error)
        setCounterServiceStatus({ status: 'Error' })
      })

    // storeService의 상태 가져오기
    storeServiceHealthCheck()
      .then(data => {
        setStoreServiceStatus(data)
      })
      .catch(error => {
        console.error('Failed to fetch store service health:', error)
        setStoreServiceStatus({ status: 'Error' })
      })
  }, [])

  return (
    <Container>
      <ServiceStatus>
        User Service 상태: {userServiceStatus.status} ({userServiceStatus.url})
      </ServiceStatus>
      <ServiceStatus>
        Counter Service 상태: {counterServiceStatus.status} (
        {counterServiceStatus.url})
      </ServiceStatus>
      <ServiceStatus>
        Store Service 상태: {storeServiceStatus.status} (
        {storeServiceStatus.url})
      </ServiceStatus>
      <Heading>[ 사용자 목록 ]</Heading>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
        <Table>
          <TableHead>
            <TableRow>
              <TableHeaderCell>User ID</TableHeaderCell>
              <TableHeaderCell>Name</TableHeaderCell>
              <TableHeaderCell>Email</TableHeaderCell>
            </TableRow>
          </TableHead>
          <tbody>
            {users &&
              users.map(user => (
                <TableRow key={user.userId}>
                  <TableCell>{user.userId}</TableCell>
                  <TableCell>{user.name}</TableCell>
                  <TableCell>{user.email}</TableCell>
                </TableRow>
              ))}
          </tbody>
        </Table>
      )}
      <StyledLink href="http://172.10.40.174:30010/" target="_blank">
        <Heading>[ Swagger 문서 ]</Heading>
      </StyledLink>
      <StyledLink href="http://172.10.40.174:30007" target="_blank">
        <Heading>[ Eureka 서버 ]</Heading>
      </StyledLink>
      <StyledLink href="http://172.10.40.174:30100/zipkin/" target="_blank">
        <Heading>[ ZIPKIN 서버 ]</Heading>
      </StyledLink>
      <StyledLink
        href="http://172.10.40.152:8989/ui/clusters/localhost/all-topics/coffee-store-ordered-events"
        target="_blank"
      >
        <Heading>[ Kafka - 커피 주문 ]</Heading>
      </StyledLink>
      <StyledLink
        href="http://172.10.40.152:8989/ui/clusters/localhost/all-topics/coffee-store-ordered-update-events"
        target="_blank"
      >
        <Heading>[ Kafka - 커피 주문 완료 ]</Heading>
      </StyledLink>
      <StyledLink href="http://172.10.40.152:15672/#/" target="_blank">
        <Heading>[ RabbitMQ 서버 ]</Heading>
      </StyledLink>
      <StyledLink href="http://172.10.40.152:9090/" target="_blank">
        <Heading>[ Prometheus Dashboard ]</Heading>
      </StyledLink>
      <StyledLink href="http://172.10.40.152:3000/dashboards" target="_blank">
        <Heading>[ Grafana Dashboard ]</Heading>
      </StyledLink>
      <StyledLink
        href="https://github.com/Jake-huen/siren-order-config"
        target="_blank"
      >
        <Heading>[ Git Config 주소 ]</Heading>
      </StyledLink>
    </Container>
  )
}

export default MyPage
