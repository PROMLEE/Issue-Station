import {
  Footer,
  FooterCopyright,
  FooterLink,
  FooterLinkGroup,
} from "flowbite-react";

export function Component() {
  return (
    <Footer container>
      <FooterCopyright href="/" by="Issue Station" year={2024} />
      <FooterLinkGroup>
        <FooterLink href="/">Home</FooterLink>
        <FooterLink href="https://github.com/SE-Issue-Mgmt-Sys">
          github
        </FooterLink>
      </FooterLinkGroup>
    </Footer>
  );
}
