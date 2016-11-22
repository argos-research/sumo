function lanePosition = getLanePosition(personID)
%getLanePosition Get the position of the person along the lane.
%   lanePosition = getLanePosition(PERSONID) Returns the position of the 
%   person along the lane measured in m.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLanePosition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
lanePosition = traci.person.getUniversal(constants.VAR_LANEPOSITION, personID);