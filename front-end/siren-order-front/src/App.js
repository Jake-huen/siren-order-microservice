import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Header from './Components/Header'
import Home from './Routes/Home'
import Search from './Routes/Search'
import Admin from './Routes/Admin'
import LoginPage from './Routes/LoginPage'
import SignupPage from './Routes/SignupPage'
import ProtectedRoute from './Routes/ProtectedRoute'
import MyPage from './Routes/MyPage'

function App() {
  return (
    <Router>
      <Header />
      <Switch>
        <Route path="/admin">
          <ProtectedRoute children={<Admin />} />
        </Route>
        <Route path="/search">
          <ProtectedRoute children={<Search />} />
        </Route>
        <Route path="/login">
          <LoginPage />
        </Route>
        <Route path="/signup">
          <SignupPage />
        </Route>
        <Route path="/myPage">
          <ProtectedRoute children={<MyPage />} />
        </Route>
        <Route path={['/', '/coffees/:coffeeId']}>
          <ProtectedRoute children={<Home />} />
        </Route>
      </Switch>
    </Router>
  )
}

export default App
