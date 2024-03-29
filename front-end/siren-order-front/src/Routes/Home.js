import { useQuery } from 'react-query'
import { getCoffees } from '../api'
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
  height: 400px;
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
  top: -80px;
`

function Home() {
  const history = useHistory()
  const bigCoffeeMatch = useRouteMatch('/coffees/:coffeeId')
  const { scrollY, scrollYProgress } = useScroll()
  const { data, isLoading } = useQuery(['coffees', 'coffeeName'], getCoffees)
  // console.log(data?.[0])
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
    bigCoffeeMatch?.params.coffeeId &&
    data.find(coffee => coffee.coffeeId === bigCoffeeMatch.params.coffeeId)
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
                        width="400px"
                      />
                      <BigTitle>{clickedCoffee.coffeeName}</BigTitle>
                      <BigOverView>
                        {clickedCoffee.coffeeDescription}
                      </BigOverView>
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
