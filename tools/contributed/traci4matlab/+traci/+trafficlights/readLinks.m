function signals = readLinks(result)
%readLinks Internal function to read the links controlled by the traffic lights.   

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: readLinks.m 31 2016-09-28 15:16:56Z afacostag $

result.readLength();
nbSignals = result.readInt(); % Length
signals = cell(1,nbSignals);
for i=1:nbSignals
    result.read(1);                           % Type of Number of Controlled Links
    nbControlledLinks = result.readInt();       % Number of Controlled Links
    controlledLinks = cell(1,nbControlledLinks);
    for j=1:nbControlledLinks
        result.read(1);                       % Type of Link j
        link = result.readStringList();       % Link j
        controlledLinks{j} = link;
    end
    signals(i) = controlledLinks;
end