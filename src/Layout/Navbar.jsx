import {
  Button,
  Popover,
  Navbar,
  NavbarBrand,
  NavbarCollapse,
  NavbarLink,
  NavbarToggle,
} from "flowbite-react";
import { Link, useNavigate } from "react-router-dom";
import { loginstate } from "../recoil/user";
import { useRecoilState } from "recoil";

export const NavComponent = () => {
  const [user, setuser] = useRecoilState(loginstate);
  const nav = useNavigate();
  const logout = () => {
    localStorage.removeItem("accessToken");
    setuser({ islogin: false, nickname: "" });
    nav("/");
  };
  return (
    <Navbar className="bg-slate-300">
      <NavbarBrand href="/">
        <img
          src="/assets/logo.png"
          alt="logo"
          className="h-10 w-10 mr-4 rounded-md"
        />
        <span className="self-center whitespace-nowrap text-3xl font-bold">
          이슈역
        </span>
      </NavbarBrand>
      <div className="md:order-2 items-center flex">
        <Popover
          aria-labelledby="default-popover"
          content={
            <div className="w-64 text-sm text-gray-500 ">
              <div className="border-b border-gray-200 bg-gray-100 px-3 py-2 dark:border-gray-600 dark:bg-gray-700">
                <div id="default-popover" className="text-gray-900 ">
                  프로젝트 명 검색하기
                </div>
              </div>
              <div className="px-3 py-2 ">
                <input className="h-7 w-5/6 border-2 border-gray-200 focus:outline-none" />
                <Link to="/projectlist">
                  <button className="h-7 text-sm w-1/6 bg-gray-200">→</button>
                </Link>
              </div>
            </div>
          }
        >
          <button className="py-2 px-3 rounded-md text-sm font-medium hover:bg-gray-50">
            Search PJ 🔍
          </button>
        </Popover>
        {user.islogin ? (
          <div className="flex gap-2 mr-1 rounded-lg px-4 py-2 text-sm font-medium text-gray-800 md:mr-2 md:px-5 md:py-2.5 ">
            <svg
              class="w-6 h-6 text-gray-800 dark:text-white"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              fill="white"
              viewBox="0 0 24 24"
            >
              <path
                fill-rule="evenodd"
                d="M12 20a7.966 7.966 0 0 1-5.002-1.756l.002.001v-.683c0-1.794 1.492-3.25 3.333-3.25h3.334c1.84 0 3.333 1.456 3.333 3.25v.683A7.966 7.966 0 0 1 12 20ZM2 12C2 6.477 6.477 2 12 2s10 4.477 10 10c0 5.5-4.44 9.963-9.932 10h-.138C6.438 21.962 2 17.5 2 12Zm10-5c-1.84 0-3.333 1.455-3.333 3.25S10.159 13.5 12 13.5c1.84 0 3.333-1.455 3.333-3.25S13.841 7 12 7Z"
                clip-rule="evenodd"
              />
            </svg>

            {user.nickname}
          </div>
        ) : (
          <a
            href="/login"
            className="mr-1 rounded-lg px-4 py-2 text-sm font-medium text-gray-800 hover:bg-gray-50 focus:outline-none focus:ring-4 focus:ring-gray-300 md:mr-2 md:px-5 md:py-2.5 "
          >
            Login
          </a>
        )}
        {user.islogin ? (
          <Button onClick={logout}>Logout</Button>
        ) : (
          <Button href="/signup">Sign up</Button>
        )}
      </div>
      <NavbarToggle />
      <NavbarCollapse>
        <NavbarLink href="/" className="h-full">
          Home
        </NavbarLink>
        <NavbarLink href="/team">Team</NavbarLink>
        <NavbarLink href="/projectlist">Project List</NavbarLink>
        <NavbarLink href="/aboutus">About us</NavbarLink>
      </NavbarCollapse>
    </Navbar>
  );
};
