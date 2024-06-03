import {
  Button,
  Label,
  Modal,
  TextInput,
  Textarea,
  Select,
} from "flowbite-react";
import { useState } from "react";
import { CreateIssue, SetReporter } from "../apis/issue";
import { loginstate } from "../recoil/user";
import { useRecoilValue } from "recoil";
import { useNavigate } from "react-router-dom";

const defaultIssue = {
  title: "기존 이슈 제목",
  description: "기존 이슈 설명...",
  private: true,
};

export function IssueCreate({ edit = false, pid, role }) {
  const navigate = useNavigate();
  const user = useRecoilValue(loginstate);
  const [openModal, setOpenModal] = useState(false);
  const [title, settitle] = useState(edit ? defaultIssue["title"] : "");
  const [description, setdescription] = useState(
    edit ? defaultIssue["description"] : ""
  );
  const [priority, setpriority] = useState("MAJOR");

  function onCloseModal() {
    setOpenModal(false);
  }

  const handleCreateIssue = async () => {
    const result = await CreateIssue(pid, {
      name: title,
      description: description,
      priority: priority,
    });
    SetReporter(result.data.result.id, user.nickname);
    alert("이슈가 생성되었습니다.");
    navigate(`/issue/${result.data.result.id}`, { state: { role } });
    settitle("");
    setdescription("");
    onCloseModal();
  };

  return (
    <>
      <Button onClick={() => setOpenModal(true)} className="h-10">
        {edit ? "Edit Issue" : "Create Issue"}
      </Button>
      <Modal show={openModal} size="md" onClose={onCloseModal} popup>
        <Modal.Header />
        <Modal.Body>
          <div className="space-y-6">
            <h3 className="text-xl font-bold text-gray-900 dark:text-white">
              {edit ? "이슈 수정하기" : "이슈 생성하기"}
            </h3>
            <div>
              <div className="mb-2 block">
                <Label htmlFor="title" value="title" />
              </div>
              <TextInput
                id="title"
                placeholder="이슈 제목 작성"
                value={title}
                onChange={(event) => settitle(event.target.value)}
                required
              />
            </div>
            <div>
              <div className="mb-2 block">
                <Label htmlFor="description" value="description" />
              </div>
              <Textarea
                id="description"
                value={description}
                onChange={(event) => setdescription(event.target.value)}
                placeholder="이슈 설명 작성..."
                required
                rows={4}
              />

              <div className="my-3 block">
                <Label htmlFor="description" value="priority" />
              </div>
              <Select
                id="priority"
                value={priority}
                onChange={(e) => setpriority(e.target.value)}
              >
                <option>BLOCKER</option>
                <option>CRITICAL</option>
                <option>MAJOR</option>
                <option>MINOR</option>
                <option>TRIVIAL</option>
              </Select>
            </div>
            <div className="w-full">
              <Button onClick={handleCreateIssue}>complete</Button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}
