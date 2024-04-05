const BASE_PATH = 'http://172.10.40.174:30010'

export function getUserIdFromLocalStorage() {
  const recoilPersistData = localStorage.getItem('recoil-persist')
  if (!recoilPersistData) {
    return null
  }

  const parsedData = JSON.parse(recoilPersistData)
  const userId = parsedData.UserId
  return userId
}

export function getTokenFromLocalStorage() {
  const recoilPersistData = localStorage.getItem('recoil-persist')
  if (!recoilPersistData) {
    return null
  }

  const parsedData = JSON.parse(recoilPersistData)

  const token = parsedData.TokenAtom
  return token
}

// 커피 조회
export function getCoffees() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/store-service/coffee`, {
    headers: {
      Authorization: token,
    },
  }).then(response => response.json())
}

// 커피 메뉴 확인
export function getCoffeeMenuByName(coffeeName) {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/store-service/coffee/${coffeeName}`, {
    headers: {
      Authorization: token,
    },
  }).then(response => response.json())
}

export function submitOrder(userId, coffeeName, qty) {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/counter-service/orders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({
      userId,
      coffeeName,
      qty,
    }),
  }).then(response => response.json())
}

export function login(email, password) {
  return fetch(`${BASE_PATH}/user-service/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, password }),
  }).then(response => {
    return response.json()
  })
}

export function signup(email, name, pwd) {
  return fetch(`${BASE_PATH}/user-service/users`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, name, pwd }),
  }).then(response => {
    return response.json()
  })
}

export function userServiceHealthCheck() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/user-service/health_check`, {
    headers: {
      Authorization: token,
    },
  })
}

export function counterServiceHealthCheck() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/counter-service/health_check`, {
    headers: {
      Authorization: token,
    },
  })
}

export function storeServiceHealthCheck() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/store-service/health_check`, {
    headers: {
      Authorization: token,
    },
  })
}

export function getAllUsers() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/user-service/users`, {
    headers: {
      Authorization: token,
    },
  }).then(response => {
    return response.json()
  })
}

export function getUserOrders(userId) {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/counter-service/${userId}/orders`, {
    headers: {
      Authorization: token,
    },
  }).then(response => {
    return response.json()
  })
}

export function getSuccessOrders() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/counter-service/orders-success`, {
    headers: {
      Authorization: token,
    },
  }).then(response => {
    return response.json()
  })
}

export function getFailedOrders() {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/counter-service/orders-failed`, {
    headers: {
      Authorization: token,
    },
  }).then(response => {
    return response.json()
  })
}

// 메뉴 수정
export function editMenu({
  coffeeName,
  unitPrice,
  coffeeBrewTime,
  coffeeImage,
  coffeeDescription,
}) {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/store-service/coffee/${coffeeName}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({
      coffeeName,
      unitPrice,
      coffeeBrewTime,
      coffeeImage,
      coffeeDescription,
    }),
  }).then(response => response.json())
}

// 메뉴 등록
export function newMenu({
  coffeeId,
  coffeeName,
  stock,
  unitPrice,
  coffeeBrewTime,
  coffeeImage,
  coffeeDescription,
}) {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/store-service/coffee`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
    body: JSON.stringify({
      coffeeName,
      unitPrice,
      coffeeBrewTime,
      coffeeImage,
      coffeeDescription,
    }),
  }).then(response => response.json())
}

export function deleteMenu({ coffeeName }) {
  const token = getTokenFromLocalStorage()
  return fetch(`${BASE_PATH}/store-service/coffee/${coffeeName}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  }).then(response => response.json())
}
