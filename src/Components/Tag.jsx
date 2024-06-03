import { Badge } from "flowbite-react";

const TagList = {
  NEW: "info",
  ASSIGNED: "green",
  FIXED: "warning",
  RESOLVED: "success",
  CLOSED: "indigo",
  REOPENED: "failure",
  PL: "purple",
  TESTER: "gray",
  DEVELOPER: "pink",
  BLOKER: "gray",
  CRITICAL: "indigo",
  MAJOR: "green",
  MINOR: "yellow",
  TRIVIAL: "red",
};

export const Tag = ({ status }) => {
  return (
    <Badge size="sm" color={TagList[status]}>
      {status}
    </Badge>
  );
};
