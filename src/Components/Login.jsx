import { Button, Label, TextInput } from "flowbite-react";

export function LoginComponent() {
  return (
    <form className="flex max-w-md flex-col gap-4 mx-auto">
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
      <Button type="submit">로그인</Button>
    </form>
  );
}
