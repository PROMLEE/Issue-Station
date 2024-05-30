import { Button, Label, Modal, TextInput, Textarea } from "flowbite-react";
import { useState } from "react";
import { CreateIssue } from "../apis/issue";
const defaultIssue = {
  title: "기존 이슈 제목",
  description: "기존 이슈 설명...",
  private: true,
};

export function IssueCreate({ edit = false, pid }) {
  const [openModal, setOpenModal] = useState(false);
  const [title, settitle] = useState(edit ? defaultIssue["title"] : "");
  const [description, setdescription] = useState(
    edit ? defaultIssue["description"] : ""
  );
  function onCloseModal() {
    setOpenModal(false);
  }

  const handleCreateIssue = async () => {
    CreateIssue(pid, {
      name: title,
      description: description,
    });
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
                <Label htmlFor="description" />
              </div>
              <Textarea
                id="description"
                value={description}
                onChange={(event) => setdescription(event.target.value)}
                placeholder="이슈 설명 작성..."
                required
                rows={4}
              />
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
