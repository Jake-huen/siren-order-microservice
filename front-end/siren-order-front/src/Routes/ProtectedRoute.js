import { useRecoilValue } from 'recoil'
import { isLoginSelector } from '../recoil/TokenAtom'
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min'
import Swal from 'sweetalert2'

const ProtectedRoute = ({ children }) => {
  const isLogin = useRecoilValue(isLoginSelector)
  const history = useHistory()
  if (isLogin) {
    console.log(children)
    return children
  } else {
    Swal.fire({
      text: '로그인 후 사용가능합니다',
      icon: 'warning',
      confirmButtonText: 'OK!',
    })
    history.push(`/login`)
  }
}

export default ProtectedRoute
