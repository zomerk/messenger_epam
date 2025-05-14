package com.epam.ld.module2.testing.template;

/**
 * The type Template.
 */
public class Template {
        private String templateString;

        public Template() {
            this.templateString = "";
        }

        public Template(String templateString) {
            this.templateString = templateString;
        }

        public String getTemplateString() {
            return templateString;
        }

        public void setTemplateString(String templateString) {
            this.templateString = templateString;
        }
}
