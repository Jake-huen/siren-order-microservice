import { useLocation } from 'react-router-dom/cjs/react-router-dom.min'

function Search() {
  const location = useLocation()
  const keyword = new URLSearchParams(location.serach).get('keyword')
  return null
}

export default Search
