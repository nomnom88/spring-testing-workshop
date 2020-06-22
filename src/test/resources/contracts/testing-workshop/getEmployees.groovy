import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return list of Employees"

    request {
        url "/api/employees"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body (
                employees: [
                        [
                            id: anyUuid(),
                            firstName: "Hardcore",
                            lastName: "Henry"
                        ],
                        [
                                id: anyUuid(),
                                firstName: "Duncan",
                                lastName: "Campbell"
                        ],
                        [
                                id: anyUuid(),
                                firstName: "Vladimir",
                                lastName: "Putin"
                        ]
                ]
        )
    }
}