import {
  Button,
  Popover,
  Navbar,
  NavbarBrand,
  NavbarCollapse,
  NavbarLink,
  NavbarToggle,
} from "flowbite-react";

export const NavComponent = () => {
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
                <h3 id="default-popover" className="text-gray-900 ">
                  프로젝트 명 검색하기
                </h3>
              </div>
              <div className="px-3 py-2 ">
                <input className="h-7 w-5/6 border-2 border-gray-200 focus:outline-none" />
                <button className="h-7 text-sm w-1/6 bg-gray-200">→</button>
              </div>
            </div>
          }
        >
          <button className="py-2 px-3 rounded-md text-sm font-medium hover:bg-gray-50">
            Search PJ 🔍
          </button>
        </Popover>
        <a
          href="/login"
          className="mr-1 rounded-lg px-4 py-2 text-sm font-medium text-gray-800 hover:bg-gray-50 focus:outline-none focus:ring-4 focus:ring-gray-300 md:mr-2 md:px-5 md:py-2.5 "
        >
          Login
        </a>
        <Button href="/signup">Sign up</Button>
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
