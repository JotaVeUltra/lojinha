package br.com.joao.lojinha.bean;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ManagedBean
@RequestScoped
public class ImageBean {
    // TODO de-hardcode
    public StreamedContent getImg() throws IOException {
        StreamedContent img = null;
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String imageId = context.getExternalContext().getRequestParameterMap().get("parCodigo");
            File f = new File("/home/joao/Projetos/Java/lojinha/uploads/produtos/" + imageId);
            if (f.isFile()) {
                Path path = Paths.get(f.getAbsolutePath());
                InputStream stream = Files.newInputStream(path);
                img = new DefaultStreamedContent(stream);
            } else {
                Path path = Paths.get("/home/joao/Projetos/Java/lojinha/uploads/branco");
                InputStream stream = Files.newInputStream(path);
                img = new DefaultStreamedContent(stream);
            }
            return img;
        }
    }
}
