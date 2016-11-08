function color = getColor(polygonID)
%getColor Get the color of the polygon.
%   color = getColor(POLYGONID) Returns the rgba color of the given polygon.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getColor.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
color = traci.polygon.getUniversal(constants.VAR_COLOR, polygonID);