import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import styled from 'styled-components'
import { useRecoilState, useSetRecoilState } from 'recoil'
import { UserIdAtom, TokenAtom } from '../recoil/TokenAtom'
import { signup } from '../api'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 250px;
`

const Title = styled.h2`
  font-size: 24px;
  margin-bottom: 20px;
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

const LoginLink = styled.p`
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

function SignupPage() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const setUserId = useSetRecoilState(UserIdAtom)
  const setAccessToken = useSetRecoilState(TokenAtom)
  const history = useHistory()
  const handleSignup = () => {
    // 회원가입 로직
    signup(email, username, password).then(({ email, username, password }) => {
      setAccessToken('')
      setUserId('')
      history.push(`/`)
    })
  }

  return (
    <Container>
      <Title>회원가입</Title>

      <Input
        type="text"
        placeholder="이름"
        value={username}
        onChange={e => setUsername(e.target.value)}
      />
      <Input
        type="email"
        placeholder="이메일"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />
      <Input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <Button onClick={handleSignup}>회원가입</Button>
      <LoginLink>
        이미 계정이 있으신가요? <Link to="/login">로그인</Link>
      </LoginLink>
    </Container>
  )
}

export default SignupPage
