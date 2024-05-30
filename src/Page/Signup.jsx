import { Button, Label, TextInput } from "flowbite-react";
import { useState } from "react";
import { Signup, CheckId, CheckNickname } from "../apis/user";
export function SignupComponent() {
  const [signup, setSignup] = useState({
    id: "",
    password: "",
    password2: "",
    nickname: "",
  });
  const [buttonon, setButtonon] = useState({
    id: false,
    nickname: false,
    password: signup.password === signup.password2 ? true : false,
  });
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (buttonon.id === false) {
      alert("아이디 중복확인을 해주세요");
      return;
    }
    if (buttonon.nickname === false) {
      alert("닉네임 중복확인을 해주세요");
      return;
    }
    if (buttonon.password === false) {
      alert("비밀번호가 일치하지 않습니다");
      return;
    }
    const response = await Signup({
      loginId: signup.id,
      loginPw: signup.password,
      nickname: signup.nickname,
    });
    if (response.status === 200) {
      alert("회원가입 성공");
    } else {
      alert("회원가입 실패");
    }
  };
  const handleCheckId = async (e) => {
    e.preventDefault();
    if (signup.id === "") {
      alert("아이디를 입력해주세요");
      return;
    }
    const response = await CheckId(signup);
    if (response.data.result) {
      setButtonon({ ...buttonon, id: true });
    } else {
      setButtonon({ ...buttonon, id: false });
      alert("아이디 중복");
    }
  };

  const handleCheckNickname = async (e) => {
    e.preventDefault();
    if (signup.nickname === "") {
      alert("닉네임을 입력해주세요");
      return;
    }
    const response = await CheckNickname(signup);
    if (response.data.result) {
      setButtonon({ ...buttonon, nickname: true });
    } else {
      setButtonon({ ...buttonon, nickname: false });
      alert("닉네임 중복");
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
        <div className="flex gap-2">
          <TextInput
            className="w-4/6"
            type="id"
            required
            shadow
            onChange={(e) => {
              setButtonon({ ...buttonon, id: false });
              setSignup({ ...signup, id: e.target.value });
            }}
          />
          <Button className="w-2/6" onClick={handleCheckId}>
            중복확인
          </Button>
        </div>
        {buttonon.id === false ? (
          <div className="text-red-500 font-bold">ID 중복을 확인해주세요</div>
        ) : (
          <div className="text-green-500">중복 확인 완료</div>
        )}
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
            setButtonon({
              ...buttonon,
              password: e.target.value === signup.password2 ? true : false,
            });
            setSignup({ ...signup, password: e.target.value });
          }}
        />
      </div>
      <div>
        <div className="mb-2 block">
          <Label value="Repeat password" />
        </div>
        <TextInput
          type="password"
          required
          shadow
          onChange={(e) => {
            setButtonon({
              ...buttonon,
              password: e.target.value === signup.password ? true : false,
            });
            setSignup({ ...signup, password2: e.target.value });
          }}
        />
      </div>{" "}
      <div>
        <div className="mb-2 block">
          <Label value="닉네임" />
        </div>
        <div className="flex gap-2">
          <TextInput
            className="w-4/6"
            type="text"
            required
            shadow
            onChange={(e) => {
              setButtonon({ ...buttonon, nickname: false });
              setSignup({ ...signup, nickname: e.target.value });
            }}
          />
          <Button className="w-2/6" onClick={handleCheckNickname}>
            중복확인
          </Button>
        </div>
        {buttonon.nickname === false ? (
          <div className="text-red-500 font-bold">
            닉네임 중복을 확인해주세요
          </div>
        ) : (
          <div className="text-green-500">중복 확인 완료</div>
        )}
      </div>
      <Button
        type="submit"
        disabled={!(buttonon.id && buttonon.nickname && buttonon.password)}
      >
        회원가입
      </Button>
    </form>
  );
}
