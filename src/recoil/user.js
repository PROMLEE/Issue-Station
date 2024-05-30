import { atom } from "recoil";

export const loginstate = atom({
  key: "user",
  default: {
    islogin: false,
    nickname: "",
  },
});
