import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import Header from './Components/Header'
import Home from './Routes/Home'
import Coffee from './Routes/Coffee'
import Search from './Routes/Search'
import Admin from './Routes/Admin'

function App() {
  return (
    <Router>
      <Header />
      <Switch>
        <Route path={['/', '/coffees/:coffeeId']}>
          <Home />
        </Route>
        <Route path="/coffee">
          <Coffee />
        </Route>
        <Route path="/admin">
          <Admin />
        </Route>
        <Route path="/search">
          <Search />
        </Route>
      </Switch>
    </Router>
  )
}

export default App
