package org.example

import com.structurizr.Workspace
import com.structurizr.model.Tags
import com.structurizr.view.Shape

fun main() {
    println("Hello World!")
    val workspace = Workspace("Getting Started", "This is a model of my software system.")
    val model = workspace.model

    val user = model.addPerson("User", "A user of my software")
    val softwareSystem = model.addSoftwareSystem("Software System", "My software system.")
    user.uses(softwareSystem, "Uses")

    val views = workspace.views
    val contextView = views.createSystemContextView(softwareSystem,
        "SystemContext", "An example of a System Context diagram.")
    contextView.addAllElements()
    contextView.addAllPeople()

    val styles = views.configuration.styles
    styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff")
    styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person)


    

}