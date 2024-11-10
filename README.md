
## API Documentation 🚀

<h3 style="color: #ff9904">Create User - POST</h3>

```
  /api/user
```

| Body Params | Type     | Example        |
|:------------|:---------|:---------------|
| `firstName` | `string` | John           |
| `lastName`  | `string` | Doe            |
| `email`     | `string` | john@email.com |
| `role`      | `string` | TEACHER        |
| `password`  | `string` | 123456         |

<h3 style="color: #ff9904">Authentication - POST</h3>
The request creates a token for user session

```
  /api/user/auth
```

| Body Params | Type     | Example        |
|:------------|:---------|:---------------|
| `email`     | `string` | jhon@email.com |
| `password`  | `string` | 123456         |


<h3 style="color: #107c10">Show Profile - GET</h3>
The request gets data about the authentication user in the application.

```
  /api/user/profile
```

<h3 style="color: #107c10">List Courses - GET</h3>
Show all courses registered.

```
  /api/courses/list
```

<h3 style="color: #107c10">List courses with query params - GET</h3>
Show all courses that match the filter

```
  /api/courses/list
```
| Query Params | Type     | Example |
|:-------------|:---------|:--------|
| `name`       | `string` | python  |
| `category`   | `string` | ai      |


> [!NOTE]
> All requests below require an authenticated user of the TEACHER type.

> [!TIP]
> the DELETE, PUT and PATCH methods can only be completed by the course owner.

<h3 style="color: #ff9904">Create a course - POST</h3>

```
  /api/courses
```
| Body Params | Type     | Example              |
|:------------|:---------|:---------------------|
| `name`      | `string` | Python for beginners |
| `category`  | `string` | AI Development       |

<h3 style="color: #e9190c">Delete a course - DELETE</h3>

```
  /api/courses/{id}
```
| Path Params | Type   | Example            |
|:------------|:-------|:-------------------|
| `id`        | `UUID` | UUID string format |


<h3 style="color: #007acc">Update a course - PUT</h3>

```
  /api/courses/{id}
```
| Path Params | Type   | Example            |
|:------------|:-------|:-------------------|
| `id`        | `UUID` | UUID string format |

| Body Params | Type     | Example            |
|:------------|:---------|:-------------------|
| `name`      | `string` | Java for beginners |
| `category`  | `string` | Development        |


<h3 style="color: #6e57d2">Change status course - PATCH</h3>
The request changes the course status, between active and inactive

```
  /api/courses/{id}/active
```
| Path Params | Type   | Example            |
|:------------|:-------|:-------------------|
| `id`        | `UUID` | UUID string format |
