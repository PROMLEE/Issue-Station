import { Badge } from "flowbite-react";

const TagList = {
  NEW: "info",
  FIXED: "warning",
  RESOLVED: "success",
  CLOSED: "indigo",
  REOPENED: "Failure",
  PL: "purple",
  TESTER: "gray",
  DEVELOPER: "pink",
};

export const Tag = ({ status }) => {
  return (
    <Badge size="sm" color={TagList[status]}>
      {status}
    </Badge>
  );
};
