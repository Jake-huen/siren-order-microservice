const BASE_PATH = 'http://172.10.40.174:30010'

export function getCoffees() {
  return fetch(`${BASE_PATH}/store-service/coffee`).then(response =>
    response.json()
  )
}

export function getCoffeeMenuByName(coffeeName) {
  console.log(coffeeName)
  return fetch(`${BASE_PATH}/store-service/coffee/${coffeeName}`).then(
    response => response.json()
  )
}
