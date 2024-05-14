import { Button, Label, TextInput } from "flowbite-react";

export function SignupComponent() {
  return (
    <form className="flex max-w-md flex-col gap-4 mx-auto">
      <div>
        <div className="mb-2 block">
          <Label value="닉네임" />
        </div>
        <TextInput type="text" required shadow />
      </div>
      <div>
        <div className="mb-2 block">
          <Label value="Id" />
        </div>
        <TextInput type="id" required shadow />
      </div>
      <div>
        <div className="mb-2 block">
          <Label value="password" />
        </div>
        <TextInput type="password" required shadow />
      </div>
      <div>
        <div className="mb-2 block">
          <Label value="Repeat password" />
        </div>
        <TextInput type="password" required shadow />
      </div>
      <Button type="submit">회원가입</Button>
    </form>
  );
}
