import {
  Button,
  Checkbox,
  Label,
  Modal,
  TextInput,
  Textarea,
} from "flowbite-react";
import { useState } from "react";

const defaultIssue = {
  title: "기존 이슈 제목",
  description: "기존 이슈 설명...",
  private: true,
};

export function IssueCreate({ edit = false }) {
  const [openModal, setOpenModal] = useState(false);
  const [title, settitle] = useState(edit ? defaultIssue["title"] : "");
  const [description, setdescription] = useState(
    edit ? defaultIssue["description"] : ""
  );
  const [privateval, setprivate] = useState(
    edit ? defaultIssue["private"] : false
  );
  function onCloseModal() {
    setOpenModal(false);
    // settitle("");
  }
  return (
    <>
      <Button onClick={() => setOpenModal(true)}>
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
            <div className="flex justify-between">
              <div className="flex items-center gap-2">
                <Checkbox
                  id="Private"
                  checked={privateval}
                  onChange={(event) => setprivate(event.target.checked)}
                />
                <Label htmlFor="Private">Private</Label>
              </div>
            </div>
            <div className="w-full">
              <Button>complete</Button>
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}
