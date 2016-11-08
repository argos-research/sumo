function position = getPosition(junctionID)
%getPosition Get the position of the junction.
%   position = getPosition(JUNCTIONID) Returns the coordinates of the 
%   center of the junction.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPosition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
position = traci.junction.getUniversal(constants.VAR_POSITION, junctionID);