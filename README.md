# Company Management Thymeleaf

Spring Boot + Thymeleaf + MyBatis で構成した、社内管理システム練習用プロジェクトです。
React 版の画面構成を、サーバーサイドレンダリング中心の構成へ置き換えています。

## Tech Stack

- Java 17
- Spring Boot 3.5.14
- Thymeleaf
- MyBatis
- MySQL 8
- Flyway
- Docker Compose

## Scope

現時点の主な対象は以下です。

- スタッフ情報管理
- 取引先情報管理
- 一覧 / 詳細 / 新規登録 / 更新
- Flyway によるスキーマ管理
- MyBatis による SQL ベース実装

## Implemented Screens

### Staff

| Screen | URL | Notes |
| --- | --- | --- |
| Staff list | `/staff/list` | 検索、ページング、新規登録、編集、削除 |
| Staff detail/update | `/staff/detail/{id}` | 詳細表示、更新 |
| Staff create | `/staff/create` | 新規登録、社員番号自動生成 |

関連機能:

- 郵便番号から住所補完
- 写真アップロード
- 履歴書アップロード
- 一部マスタ連動制御

### Customer

| Screen | URL | Notes |
| --- | --- | --- |
| Customer list | `/cust/list` | 検索、ページング、新規登録、論理削除 |
| Customer detail/update | `/cust/detail/{id}` | 詳細表示、更新 |
| Customer create | `/cust/create` | 新規登録、担当者行追加、銀行検索 |

関連機能:

- 担当者行の追加 / 削除
- 銀行マスタ検索
- 郵便番号検索
- 論理削除 (`CUST_DELFLG = '1'`)

## Project Structure

```text
company-management-thymeleaf/
├── backend/
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── mapper/
│   │   ├── templates/
│   │   ├── static/
│   │   └── db/migration/
│   └── pom.xml
├── docker-compose.yml
└── README.md
```

## Run With Docker

```bash
docker compose up --build
```

起動後:

- App: [http://localhost:8082](http://localhost:8082)
- Staff list: [http://localhost:8082/staff/list](http://localhost:8082/staff/list)
- Customer list: [http://localhost:8082/cust/list](http://localhost:8082/cust/list)
- MySQL: `localhost:3309`

Docker 起動時は `prod` profile を使用します。

## Run Locally

ローカル起動時は `application.properties` で `dev` profile が有効です。
先に MySQL を起動してください。

デフォルト接続先:

```text
URL: jdbc:mysql://localhost:3306/company_management
Username: root
Password: springboot
```

起動:

```bash
cd backend
./mvnw spring-boot:run
```

起動後:

- App: [http://localhost:8080](http://localhost:8080)
- Staff list: [http://localhost:8080/staff/list](http://localhost:8080/staff/list)
- Customer list: [http://localhost:8080/cust/list](http://localhost:8080/cust/list)

## Flyway Migrations

Flyway はアプリ起動時に自動実行されます。

現在の migration:

- `V1__initial_schema.sql`
- `V2__master_data.sql`
- `V3__customer_test_data.sql`
- `V4__create_manager_table.sql`
- `V5__manager_test_data.sql`
- `V6__rename_and_fix_user_table.sql`
- `V7__user_test_data.sql`
- `V8__add_user_audit_foreign_keys.sql`
- `V9__create_bank_master.sql`

主な内容:

- 初期テーブル作成
- 汎用マスタ投入
- 取引先 / 担当者 / ユーザー / 銀行マスタ投入

## Database Notes

- `CUST_TBL` は論理削除です。
- 取引先削除時は `CUST_DELFLG = '1'` に更新します。
- 一覧系 SQL は通常 `CUST_DELFLG = '0'` を条件に含めます。
- 銀行関連の取引先項目は現在 `CUST_BRANCH_CD` を使用します。
- 旧カラム `CUST_BRANCHCD` は使用しません。

## Test

```bash
cd backend
./mvnw test
```

補足:

- 一部テストは MySQL 接続前提です。
- 単体テストと Flyway 状態がずれている場合、起動前に DB 初期化が必要になることがあります。

## Known Gaps

- Spring Security ログイン未実装
- 権限制御未実装
- 一部画面は式様書への追従を継続中
- 一部項目はまだ HTML 直書きのため、汎用マスタ連携へ寄せる余地あり

## Next Improvements

- ログイン機能実装
- 権限管理実装
- 汎用マスタ連携の拡張
- 画面レイアウトの式様書追従
