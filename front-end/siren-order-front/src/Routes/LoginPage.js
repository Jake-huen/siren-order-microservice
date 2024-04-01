import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil'
import {
  TokenAtom,
  isLoginSelector,
  userState,
  UserIdAtom,
} from '../recoil/TokenAtom'
import { login } from '../api'
import { Redirect, useHistory } from 'react-router-dom/cjs/react-router-dom.min'
import Swal from 'sweetalert2'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 250px;
`

const Title = styled.h2`
  font-size: 24px;
  margin-bottom: 20px;
`

const LoginForm = styled.form`
  display: flex;
  flex-direction: column;
`

const Input = styled.input`
  width: 300px;
  height: 40px;
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;

  &:focus {
    outline: none;
    border-color: #007bff;
  }
`

const Button = styled.button`
  width: 300px;
  height: 40px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;

  &:hover {
    background-color: #0056b3;
  }
`

const SignUpLink = styled.p`
  margin-top: 20px;
  font-size: 14px;

  a {
    color: #007bff;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
`

function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const setAccessToken = useSetRecoilState(TokenAtom)
  const setUserId = useSetRecoilState(UserIdAtom)
  const isLogin = useRecoilValue(isLoginSelector)

  const history = useHistory()
  const handleLogin = e => {
    e.preventDefault()
    login(email, password).then(({ userId, token }) => {
      console.log('userId:', userId)
      console.log('token: ', token)
      setAccessToken(token)
      setUserId(userId)
      history.push(`/`)
    })
  }

  if (isLogin) {
    Swal.fire({
      text: '마이페이지로 이동합니다.',
      timer: 1000,
    })
    return <Redirect to="/myPage" />
  }

  return (
    <Container>
      <Title>로그인</Title>
      <LoginForm onSubmit={handleLogin}>
        <Input
          type="email"
          placeholder="이메일"
          value={email}
          onChange={e => {
            setEmail(e.target.value)
          }}
        />
        <Input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={e => setPassword(e.target.value)}
        />
        <Button type="submit">로그인</Button>
        <SignUpLink>
          계정이 없으신가요? <Link to="/signup">회원가입</Link>
        </SignUpLink>
      </LoginForm>
    </Container>
  )
}

export default LoginPage
