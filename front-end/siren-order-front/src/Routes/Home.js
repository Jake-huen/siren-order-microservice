import { useQuery } from 'react-query'
import { getCoffees } from '../api'
import styled from 'styled-components'

const Wrapper = styled.div`
  background-color: black;
`

const Loader = styled.div`
  height: 20vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

const Banner = styled.div`
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 60px;
`

const Title = styled.h2`
  font-size: 48px;
  width: 50%;
  margin-bottom: 20px;
`

const OverView = styled.p`
  font-size: 36px;
`

function Home() {
  const { data, isLoading } = useQuery(['coffees', 'coffeeName'], getCoffees)
  // console.log(data, isLoading)
  console.log(data?.[0])
  return (
    <Wrapper>
      {isLoading ? (
        <Loader>Loading...</Loader>
      ) : (
        <>
          <Banner>
            <Title>{data?.[0].coffeeName}</Title>
            <OverView>{data?.[0].unitPrice}</OverView>
          </Banner>
        </>
      )}
    </Wrapper>
  )
}

export default Home
