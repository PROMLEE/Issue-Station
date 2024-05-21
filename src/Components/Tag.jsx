import { Badge } from "flowbite-react";

const TagList = {
  new: "info",
  fixed: "warning",
  resolved: "success",
  closed: "indigo",
  reopened: "Failure",
  PL: "purple",
  Tester: "gray",
  Developer: "pink",
};

export const Tag = ({ status }) => {
  return (
    <Badge size="sm" color={TagList[status]}>
      {status}
    </Badge>
  );
};
