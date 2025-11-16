# Repository Overview

- **Tech stack**: Java, Apache Struts 1.x, JSP, Maven
- **Key modules**:
  - **src/main/java**: Java sources (Actions, Forms, Models)
  - **src/main/webapp**: JSP, WEB-INF, Struts config
  - **struts-config.xml**: Action mappings, form-beans, resources
- **Build**: Maven (pom.xml), outputs in `target/`

## Application Flow
- **Form**: `com.demo.form.SearchForm` (scope=session)
- **Action**: `com.demo.action.SearchAction` (DispatchAction, parameter=action)
- **JSP**: `src/main/webapp/search.jsp`
- **Forward**: `init -> search.page`

## Validation Strategy
- `SearchForm#validate(...)` validates user inputs (date format yyyy/MM/dd and range)
- On validation errors:
  - `request` attributes: `showDateAlert` (Boolean), `dateValidationMessage` (String)
  - JSP shows JS `alert()` after page load via `window.addEventListener('load', ...)`

## Known Conventions
- Date format for inputs: `yyyy/MM/dd`
- Pagination carried via `currentPage`, `totalPages`, `pageSize`

## Tips for Maintenance
- Keep date formats consistent between Action parsing and Form validation
- When adding fields in `SearchForm`, ensure JSP binds via Struts tags
- If moving to i18n, map message keys in resource bundle `com.demo.messageResource`