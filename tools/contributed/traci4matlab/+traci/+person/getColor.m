function color = getColor(personID)
%getColor Get the color of the person.
%   color = getColor(PERSONID) Returns the person's rgba color.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
color = traci.person.getUniversal(constants.VAR_COLOR, personID);