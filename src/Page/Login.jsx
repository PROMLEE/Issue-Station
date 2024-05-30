import { Button, Label, TextInput } from "flowbite-react";
import { useState } from "react";
import { Login } from "../apis/user";
import { useNavigate } from "react-router-dom";
import { loginstate } from "../recoil/user";
import { useSetRecoilState } from "recoil";

export function LoginComponent() {
  const navigate = useNavigate();
  const [login, setLogin] = useState({ id: "", password: "" });
  const setUser = useSetRecoilState(loginstate);
  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await Login(login);
    if (response.status === 200) {
      localStorage.setItem("accessToken", response.data.data.token);
      setUser({ islogin: true, nickname: response.data.data.user.nickname });
      navigate("/");
    } else {
      alert("로그인 실패, ID와 PW를 확인해주세요.");
    }
  };
  return (
    <form
      className="flex max-w-md flex-col gap-4 mx-auto"
      onSubmit={handleSubmit}
    >
      <div>
        <div className="mb-2 block">
          <Label value="Id" />
        </div>
        <TextInput
          type="id"
          required
          shadow
          onChange={(e) => {
            setLogin({ ...login, id: e.target.value });
          }}
        />
      </div>
      <div>
        <div className="mb-2 block">
          <Label value="password" />
        </div>
        <TextInput
          type="password"
          required
          shadow
          onChange={(e) => {
            setLogin({ ...login, pw: e.target.value });
          }}
        />
      </div>
      <Button type="submit">로그인</Button>
    </form>
  );
}
