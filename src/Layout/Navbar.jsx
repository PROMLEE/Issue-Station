import {
  Button,
  Popover,
  NavbarBrand,
  NavbarCollapse,
  NavbarLink,
  NavbarToggle,
  MegaMenuDropdown,
  MegaMenuDropdownToggle,
  MegaMenu,
} from "flowbite-react";
import { HiArrowRight, HiChevronDown } from "react-icons/hi";
import { Link, useNavigate } from "react-router-dom";
import { loginstate } from "../recoil/user";
import { useRecoilState } from "recoil";
import { useState, useEffect } from "react";
import { TokenCheck } from "../apis/user";

export const NavComponent = () => {
  const [user, setuser] = useRecoilState(loginstate);
  const [search, setSearch] = useState("");
  const nav = useNavigate();
  const accessToken = localStorage.getItem("accessToken");
  const [token, setToken] = useState(accessToken);

  useEffect(() => {
    setToken(accessToken);
  }, [accessToken]);

  const logout = () => {
    localStorage.removeItem("accessToken");
    setuser({ islogin: false, nickname: "" });
    alert("로그아웃 되었습니다.");
    nav("/");
  };

  useEffect(() => {
    getNickname();
  }, [token]);

  const getNickname = async () => {
    if (token) {
      try {
        const response = await TokenCheck();
        setuser({ islogin: true, nickname: response.data.data.nickname });
      } catch (e) {
        localStorage.removeItem("accessToken");
        setuser({ islogin: false, nickname: "" });
        alert("로그아웃 되었습니다.");
        nav("/");
        console.log(e);
      }
    }
  };

  const HanddleSearch = (e) => {
    e.preventDefault();
    nav(`/projectlist`, {
      state: { search },
    });
  };

  return (
    <MegaMenu className="bg-slate-300">
      <NavbarBrand>
        <Link to="/">
          <img
            src="/assets/logo.png"
            alt="logo"
            className="h-10 w-10 mr-4 rounded-md"
          />
        </Link>
        <Link to="/">
          <span className="self-center whitespace-nowrap text-3xl font-bold">
            이슈역
          </span>
        </Link>
        <div className="items-center flex md:ml-5">
          <Popover
            aria-labelledby="default-popover"
            content={
              <div className="w-64 text-sm text-gray-500 ">
                <div className="border-b border-gray-200 bg-gray-100 px-3 py-2 dark:border-gray-600 dark:bg-gray-700">
                  <div id="default-popover" className="text-gray-900 ">
                    프로젝트 명 검색하기
                  </div>
                </div>
                <form className="px-3 py-2 " onSubmit={(e) => HanddleSearch(e)}>
                  <input
                    className="h-7 w-5/6 border-2 border-gray-200 focus:outline-none"
                    onChange={(e) => {
                      setSearch(e.target.value);
                    }}
                  />
                  <button
                    className="h-7 text-sm w-1/6 bg-gray-200"
                    type="submit"
                  >
                    →
                  </button>
                </form>
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
                className="w-6 h-6 text-gray-800 dark:text-white"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                fill="white"
                viewBox="0 0 24 24"
              >
                <path
                  fillRule="evenodd"
                  d="M12 20a7.966 7.966 0 0 1-5.002-1.756l.002.001v-.683c0-1.794 1.492-3.25 3.333-3.25h3.334c1.84 0 3.333 1.456 3.333 3.25v.683A7.966 7.966 0 0 1 12 20ZM2 12C2 6.477 6.477 2 12 2s10 4.477 10 10c0 5.5-4.44 9.963-9.932 10h-.138C6.438 21.962 2 17.5 2 12Zm10-5c-1.84 0-3.333 1.455-3.333 3.25S10.159 13.5 12 13.5c1.84 0 3.333-1.455 3.333-3.25S13.841 7 12 7Z"
                  clipRule="evenodd"
                />
              </svg>
              {user.nickname}
            </div>
          ) : (
            <Link
              to="/login"
              className="mr-1 rounded-lg px-4 py-2 text-sm font-medium text-gray-800 hover:bg-gray-50 focus:outline-none focus:ring-4 focus:ring-gray-300 md:mr-2 md:px-5 md:py-2.5 "
            >
              Login
            </Link>
          )}
          {user.islogin ? (
            <Button onClick={logout}>Logout</Button>
          ) : (
            <Link to="/signup">
              <Button>Sign up</Button>
            </Link>
          )}
        </div>
      </NavbarBrand>
      <NavbarToggle />
      <NavbarCollapse>
        <NavbarLink>
          <Link to="/">Home</Link>
        </NavbarLink>
        {/* <NavbarLink href="/myproject">MyProject</NavbarLink> */}
        {/* <NavbarLink href="/projectlist">Project List</NavbarLink> */}
        <MegaMenuDropdownToggle className="md:h-5">
          Project <HiChevronDown className="ml-2" />
        </MegaMenuDropdownToggle>
        <NavbarLink className="md:w-20">
          <Link to="/aboutus" className="md:w-20">
            About us
          </Link>
        </NavbarLink>
      </NavbarCollapse>
      <MegaMenuDropdown className="md:ml-auto w-full">
        <div className="md:ml-auto md:w-80 mt-6 border-y border-gray-200 bg-white shadow-sm  rounded-sm">
          <div className="mx-auto grid max-w-screen-xl px-4 py-2 text-sm text-gray-500 dark:text-gray-400 md:grid-cols-2">
            <ul
              className="mb-4  space-y-4 md:mb-0 md:block"
              aria-labelledby="mega-menu-full-image-button"
            >
              <li>
                <Link
                  to="/projectlist"
                  className="hover:text-primary-600 dark:hover:text-primary-500"
                >
                  Projects
                </Link>
              </li>
              <li>
                <Link
                  to="/myproject"
                  className="hover:text-primary-600 dark:hover:text-primary-500"
                >
                  My projects
                </Link>
              </li>
            </ul>
            <div className="rounded-lg bg-gray-500 p-3 text-left">
              <p className="mb-3 max-w-xl font-extrabold leading-tight tracking-tight text-white">
                Create New Project
              </p>
              <button
                type="button"
                onClick={() => nav("/createproject")}
                className="inline-flex items-center rounded-lg border border-white px-2.5 py-1.5 text-center text-xs font-medium text-white hover:bg-white hover:text-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-700"
              >
                Get started
                <HiArrowRight className="ml-2" />
              </button>
            </div>
          </div>
        </div>
      </MegaMenuDropdown>
    </MegaMenu>
  );
};
