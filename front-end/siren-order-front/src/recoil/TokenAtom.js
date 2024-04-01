import { atom, selector } from 'recoil'
import { recoilPersist } from 'recoil-persist'

const { persistAtom } = recoilPersist()

export const TokenAtom = atom({
  key: 'TokenAtom',
  effects_UNSTABLE: [persistAtom],
  default: undefined,
})

export const UserIdAtom = atom({
  key: 'UserId',
  effects_UNSTABLE: [persistAtom],
  default: undefined,
})

export const isLoginSelector = selector({
  key: 'isLoginSelector',
  effects_UNSTABLE: [persistAtom],
  get: ({ get }) => !!get(TokenAtom),
})
