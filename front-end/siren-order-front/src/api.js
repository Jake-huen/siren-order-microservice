const BASE_PATH = 'http://172.10.40.174:30010'

export function getCoffees() {
  return fetch(`${BASE_PATH}/store-service/coffee`).then(response =>
    response.json()
  )
}

export function getCoffeeMenuByName(coffeeName) {
  return fetch(`${BASE_PATH}/store-service/coffee/${coffeeName}`).then(
    response => response.json()
  )
}

export function submitOrder(userId, coffeeName, qty) {
  return fetch(`${BASE_PATH}/counter-service/orders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      userId,
      coffeeName,
      qty,
    }),
  }).then(response => response.json())
}
