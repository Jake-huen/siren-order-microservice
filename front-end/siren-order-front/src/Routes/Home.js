import { useQuery } from 'react-query'
import { getCoffees, submitOrder } from '../api'
import { styled } from 'styled-components'
import { motion, AnimatePresence, useScroll } from 'framer-motion'
import { makeImagePath } from '../utils'
import { useEffect, useState } from 'react'
import {
  useHistory,
  useRouteMatch,
} from 'react-router-dom/cjs/react-router-dom.min'
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
  background-image: linear-gradient(rgba(0, 0, 0, 1), rgba(0, 0, 0, 0)),
    url(${props => props.bgPhoto});
  background-size: cover;
  background-position: center 50%;
`

const Title = styled.h2`
  font-size: 68px;
  margin-bottom: 20px;
`

const OverView = styled.p`
  font-size: 36px;
`

const Slider = styled.div`
  position: relative;
  top: -100px;
`

const Row = styled(motion.div)`
  display: grid;
  gap: 5px;
  grid-template-columns: repeat(5, 1fr);
  position: absolute;
  width: 100%;
`

const Box = styled(motion.div)`
  background-color: white;
  background-image: url(${props => props.bgPhoto});
  background-size: cover;
  background-position: center center;
  height: 200px;
  font-size: 66px;
  cursor: pointer;
  &:first-child {
    transform-origin: center left;
  }
  &:last-child {
    transform-origin: center right;
  }
`

const Info = styled(motion.div)`
  padding: 10px;
  background-color: ${props => props.theme.black.lighter};
  opacity: 0;
  position: absolute;
  width: 100%;
  bottom: 0;
  h4 {
    text-align: center;
    font-size: 18px;
  }
`

const infoVariants = {
  hover: {
    opacity: 1,
    transition: {
      delay: 0.5,
      duration: 0.3,
      type: 'tween',
    },
  },
}

const rowVariants = {
  hidden: {
    x: window.outerWidth,
  },
  visible: {
    x: 0,
  },
  exit: {
    x: -window.outerWidth,
  },
}

const offset = 5

const boxVariants = {
  normal: {
    scale: 1,
  },
  hover: {
    zIndex: 99,
    scale: 1.3,
    y: -50,
    transition: {
      delay: 0.5,
      duration: 0.3,
      type: 'tween',
    },
  },
}

const Overlay = styled(motion.div)`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  opacity: 0;
`

const BigCofee = styled(motion.div)`
  position: absolute;
  width: 40vw;
  height: 80vh;
  left: 0;
  right: 0;
  margin: 0 auto;
  border-radius: 15px;
  overflow: hidden;
  background-color: ${props => props.theme.black.lighter};
`

const BigCover = styled.div`
  width: 100%;
  background-size: cover;
  background-position: center center;
  height: 250px;
`

const BigTitle = styled.h2`
  color: ${props => props.theme.white.lighter};
  padding: 20px;
  font-size: 36px;
  position: relative;
  top: -80px;
`

const BigOverView = styled.p`
  padding: 20px;
  color: ${props => props.theme.white.lighter};
`

const OrderForm = styled.form`
  margin-top: 20px;
  margin-left: 30px;
  margin-right: 30px;
  border: 1px solid #ccc;
  padding: 10px;
`

const QuantityInput = styled.input`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
  padding: 10px;
  width: 25%;
  font-size: 16px;
  border: 2px solid ${props => props.theme.primary};
  border-radius: 5px;
  margin-top: 10px;
  margin-bottom: 10px;
`

const PriceDisplay = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  margin-bottom: 10px;
`

const OrderButton = styled.button`
  display: flex;
  margin: 0 auto;
  justify-content: center;
  align-items: center;
  width: 50%;
  height: 50px;
  cursor: pointer;
  background-color: ${props => props.theme.white.darker};
  font-size: 18px;
  border-radius: 5px;
  transition: background-color 0.3s ease-in-out;

  &:hover {
    background-color: ${props => props.theme.white.lighter};
    font-size: 20px;
  }
`

const RemainingStock = styled.div`
  font-size: 14px;
  color: gray;
`

function Home() {
  const history = useHistory()
  const bigCoffeeMatch = useRouteMatch('/coffees/:coffeeId')
  const { scrollY, scrollYProgress } = useScroll()
  const { data, isLoading } = useQuery(['coffees', 'coffeeName'], getCoffees)
  const [index, setIndex] = useState(0)
  const [leaving, setLeaving] = useState(false)
  const toggleLeaving = () => setLeaving(prev => !prev)
  const increaseIndex = () => {
    if (data) {
      if (leaving) return
      toggleLeaving()
      const totalMenus = data?.length
      const maxIndex = Math.floor(totalMenus / offset) - 1
      setIndex(prev => (prev === maxIndex ? 0 : prev + 1))
    }
  }
  const onBoxCliecked = coffeeId => {
    history.push(`/coffees/${coffeeId}`)
  }
  const onOverlayClick = () => {
    history.push('/')
  }
  const clickedCoffee =
    Array.isArray(data) && bigCoffeeMatch?.params.coffeeId
      ? data.find(coffee => coffee.coffeeId === bigCoffeeMatch.params.coffeeId)
      : undefined

  const [quantity, setQuantity] = useState(1)

  const handleQuantityChange = e => {
    const newQuantity = parseInt(e.target.value)
    setQuantity(newQuantity)
  }

  const handleSubmit = coffeeName => {
    const quantityValue = parseInt(quantity)
    setQuantity(1)
    if (quantityValue <= 0) {
      alert('수량은 1 이상이어야 합니다.')
      return
    }
    submitOrder(
      'cbd12de0-4cd5-45af-9e10-db38f8833a53',
      coffeeName,
      quantityValue
    )
      .then(response => {
        console.log('Order response: ', response)
      })
      .catch(error => {
        console.error('Order error: ', error)
      })
  }
  return (
    <Wrapper>
      {isLoading ? (
        <Loader>Loading...</Loader>
      ) : (
        <>
          <Banner
            onClick={increaseIndex}
            bgPhoto={makeImagePath(data?.[0].coffeeImage || '')}
          >
            <Title>{data?.[0].coffeeName}</Title>
            <OverView>{data?.[0].coffeeDescription}</OverView>
          </Banner>
          <Slider>
            <AnimatePresence initial={false} onExitComplete={toggleLeaving}>
              <Row
                variants={rowVariants}
                initial="hidden"
                animate="visible"
                exit="exit"
                transition={{ type: 'tween', duration: 1 }}
                key={index}
              >
                {data
                  ?.slice(0)
                  .slice(offset * index, offset * index + offset)
                  .map(coffee => (
                    <Box
                      layoutId={coffee.coffeeId + ''}
                      key={coffee.coffeeId}
                      whileHover="hover"
                      initial="normal"
                      variants={boxVariants}
                      onClick={() => onBoxCliecked(coffee.coffeeId)}
                      transition={{ type: 'tween' }}
                      bgPhoto={makeImagePath(coffee.coffeeImage)}
                    >
                      <Info variants={infoVariants}>
                        <h4>{coffee.coffeeName}</h4>
                      </Info>
                    </Box>
                  ))}
              </Row>
            </AnimatePresence>
          </Slider>
          <AnimatePresence>
            {bigCoffeeMatch ? (
              <>
                <Overlay
                  onClick={onOverlayClick}
                  exit={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                />
                <BigCofee
                  layoutId={bigCoffeeMatch.params.coffeeId}
                  style={{ top: scrollY.get() + 100 }}
                >
                  {clickedCoffee && (
                    <>
                      <BigCover
                        style={{
                          backgroundImage: `linear-gradient(to top, black, transparent), url(${makeImagePath(
                            clickedCoffee.coffeeImage
                          )})`,
                        }}
                        width="300px"
                      />
                      <BigTitle>{clickedCoffee.coffeeName}</BigTitle>
                      <BigOverView>
                        상세 정보 : {clickedCoffee.coffeeDescription}
                      </BigOverView>
                      <OrderForm
                        onSubmit={e => {
                          e.preventDefault()
                          handleSubmit(clickedCoffee.coffeeName)
                        }}
                      >
                        <RemainingStock>
                          남은 수량 : {clickedCoffee.stock} 개
                        </RemainingStock>
                        <div>
                          <QuantityInput
                            type="number"
                            value={quantity}
                            onChange={handleQuantityChange}
                          ></QuantityInput>
                          <PriceDisplay>
                            가격 : {clickedCoffee.unitPrice} 원
                          </PriceDisplay>
                        </div>

                        <OrderButton type="submit">주문하기</OrderButton>
                      </OrderForm>
                    </>
                  )}
                </BigCofee>
              </>
            ) : null}
          </AnimatePresence>
        </>
      )}
    </Wrapper>
  )
}

export default Home
