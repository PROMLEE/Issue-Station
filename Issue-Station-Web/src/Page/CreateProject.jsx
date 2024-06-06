import {
  Button,
  Checkbox,
  Label,
  TextInput,
  Textarea,
  Progress,
  Dropdown,
} from "flowbite-react";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { JoinProject, JoinTeam } from "../apis/project";
import { CheckNickname } from "../apis/user";
import { loginstate } from "../recoil/user";
import { useRecoilValue } from "recoil";
import isLogin from "../util/checklogin";
import { Tag } from "../Components/Tag";
export const CreateProject = () => {
  const nav = useNavigate();

  const [progress, setProgress] = useState(50);
  const [project, setProject] = useState({
    name: "",
    description: "",
    isPrivate: false,
  });
  const [pid, setPid] = useState(0);
  const [searchNickname, setSearchNickname] = useState("");
  const [userList, setUserList] = useState([]);
  const [state, setState] = useState("PL");
  const user = useRecoilValue(loginstate);

  useEffect(() => {
    if (!isLogin()) {
      alert("로그인이 필요합니다.");
      nav("/login");
    }
  }, []);

  const handleNext = (e) => {
    e.preventDefault();
    sendJoinProject(e);
    setProgress(progress + 50);
  };

  const sendJoinProject = async (e) => {
    e.preventDefault();
    const response = await JoinProject(project);
    if (response.status === 200) {
      setPid(response.data.result.id);
      alert("프로젝트 생성 성공");
    } else {
      alert("프로젝트 생성 실패");
    }
  };

  const handleCheckNickname = async (e) => {
    e.preventDefault();
    const response = await CheckNickname(searchNickname);
    if (response.data.result) {
      alert("존재하지 않는 닉네임입니다.");
    } else {
      setUserList([...userList, { nickname: searchNickname, role: state }]);
    }
    setSearchNickname("");
    setState("PL");
  };

  const handleComplete = async (e) => {
    e.preventDefault();
    await JoinTeam(pid, { nickname: user.nickname, isAdmin: true, role: "PL" });
    for (let i = 0; i < userList.length; i++) {
      await JoinTeam(pid, {
        nickname: userList[i].nickname,
        isAdmin: false,
        role: userList[i].role,
      });
    }
    alert("프로젝트 생성 완료");
    nav("/myproject");
  };

  return (
    <div>
      <Progress progress={progress} className="mb-5" />
      {progress === 50 ? (
        <form
          className="flex max-w-md flex-col gap-4"
          onSubmit={(e) => handleNext(e)}
        >
          <div className="text-2xl font-bold">프로젝트 정보 작성</div>
          <div>
            <div className="mb-2 block">
              <Label value="Project Name" />
            </div>
            <TextInput
              id="name"
              type="text"
              placeholder="your project name"
              required
              shadow
              onChange={(e) => {
                setProject({ ...project, name: e.target.value });
              }}
            />
          </div>
          <div className="max-w-md">
            <div className="mb-2 block">
              <Label value="Description" />
            </div>
            <Textarea
              id="description"
              placeholder="Leave a Description..."
              required
              rows={4}
              onChange={(e) => {
                setProject({ ...project, description: e.target.value });
              }}
            />
          </div>
          <div className="flex items-center gap-2">
            <Checkbox
              id="private"
              onChange={(e) => {
                const value = e.target.value === "on" ? true : false;
                setProject({ ...project, isPrivate: value });
              }}
            />
            <Label htmlFor="agree" className="flex">
              Private
            </Label>
          </div>
          <Button type="submit">Next</Button>
        </form>
      ) : (
        <div className="flex max-w-md flex-col gap-4">
          <div className="text-2xl font-bold">팀 멤버 추가</div>
          <form className="flex gap-2" onSubmit={handleCheckNickname}>
            <TextInput
              className="w-4/6"
              type="id"
              required
              shadow
              value={searchNickname}
              onChange={(e) => {
                setSearchNickname(e.target.value);
              }}
            />
            <Dropdown label={state} inline value={state}>
              <Dropdown.Item onClick={() => setState("PL")}>PL</Dropdown.Item>
              <Dropdown.Item onClick={() => setState("TESTER")}>
                TESTER
              </Dropdown.Item>
              <Dropdown.Item onClick={() => setState("DEVELOPER")}>
                DEVELOPER
              </Dropdown.Item>
            </Dropdown>
            <Button className="w-2/6" type="submit">
              추가
            </Button>
          </form>
          {userList.map((user) => {
            return (
              <div className="flex gap-3">
                {user.nickname} <Tag status={user.role}>{user.role}</Tag>
              </div>
            );
          })}
          <Button onClick={handleComplete}>Complete</Button>
        </div>
      )}
    </div>
  );
};
